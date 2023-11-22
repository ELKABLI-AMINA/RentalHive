package ma.youcode.batispro.service;

import ma.youcode.batispro.domain.enums.BillStatus;
import ma.youcode.batispro.dto.BillDto;
import ma.youcode.batispro.exception.DossierNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBillingService {

    public List<BillDto> getAllBills(Pageable pageable);
    public List<BillDto> getAllBillsByStatus(BillStatus status, Pageable pageable);
    public BillDto getBillByNumber(String billNumber);

    public BillDto createBill(String dossierNumber) throws DossierNotFoundException;

    public BillDto updateBill(String billNumber, BillStatus billStatus);

    public void deleteBill(String billNumber);

    public BillDto payBill(String billNumber);

}
