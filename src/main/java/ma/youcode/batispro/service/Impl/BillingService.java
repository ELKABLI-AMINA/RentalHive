package ma.youcode.batispro.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.batispro.domain.entity.Bill;
import ma.youcode.batispro.domain.entity.BillDetails;
import ma.youcode.batispro.domain.entity.DossierLocation;
import ma.youcode.batispro.domain.entity.Location;
import ma.youcode.batispro.domain.enums.BillStatus;
import ma.youcode.batispro.domain.enums.Location.LocationStatus;
import ma.youcode.batispro.domain.enums.PaymentStatus;
import ma.youcode.batispro.dto.BillDto;
import ma.youcode.batispro.dto.mapper.BillDtoMapper;
import ma.youcode.batispro.exception.BillNotFoundException;
import ma.youcode.batispro.exception.BillingCreationException;
import ma.youcode.batispro.exception.DossierNotFoundException;
import ma.youcode.batispro.repository.BillingRepository;
import ma.youcode.batispro.service.IBillingService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingService implements IBillingService {

    private final BillingRepository billingRepository;
    private final BillDtoMapper billDtoMapper;
    private final LocationService locationService;
    @Override
    public List<BillDto> getAllBills(Pageable pageable) {
        return StreamSupport.stream(billingRepository.findAll(pageable).spliterator(), false)
                .map(billDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<BillDto> getAllBillsByStatus(BillStatus status, Pageable pageable) {
        return StreamSupport.stream(billingRepository.findByStatus(status, pageable).spliterator(), false)
                .map(billDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public BillDto getBillByNumber(String billNumber) {
        return billingRepository.findByBillNumber(billNumber)
                .map(billDtoMapper::mapToDto)
                .orElseThrow();
    }

    @Override
    public BillDto createBill(String dossierNumber) throws DossierNotFoundException {
        DossierLocation folder = locationService.findLocationFolderByNumber(dossierNumber);
        List<Location> list = folder.getLocation().parallelStream().filter(location -> location.getStatus().equals(LocationStatus.ACCEPTED))
                .toList();
        if(list.isEmpty()) throw new BillingCreationException("Cannot generate a bill for a dossier that has no accepted reservations");
        Bill bill = Bill.builder()
                .dateConfirmation(LocalDateTime.now())
                .billNumber("BILL-" + LocalDateTime.now().getNano())
                .client(folder.getClient())
                .dossierLocation(folder)
                .status(BillStatus.CREATED)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        Bill billSaved = billingRepository.save(bill);
        return billDtoMapper.mapToDto(billSaved);
    }

    @Override
    public BillDto updateBill(String billNumber, BillStatus billStatus) {
        return null;
    }

    @Override
    public void deleteBill(String billNumber) {
        Optional<Bill> optionalBill = billingRepository.findByBillNumber(billNumber);
        if(optionalBill.isEmpty()) throw new BillNotFoundException("Cannot find bill with number: " + billNumber);
        Bill bill = optionalBill.get();
        bill.setStatus(BillStatus.CANCELED);
        billingRepository.save(bill);
    }

    @Override
    public BillDto payBill(String billNumber) {
        Optional<Bill> optionalBill = billingRepository.findByBillNumber(billNumber);
        if(optionalBill.isEmpty()) throw new BillNotFoundException("Cannot find bill with number: " + billNumber);
        Bill bill = optionalBill.get();
        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setStatus(BillStatus.ACCEPTED); // automatically accepted if paid
        billingRepository.save(bill);
        return billDtoMapper.mapToDto(bill);
    }
}
