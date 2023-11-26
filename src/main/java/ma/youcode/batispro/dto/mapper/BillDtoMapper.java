package ma.youcode.batispro.dto.mapper;

import lombok.RequiredArgsConstructor;
import ma.youcode.batispro.domain.entity.Bill;
import ma.youcode.batispro.dto.BillDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillDtoMapper {

    private final BillDetailsDtoMapper billDetailsDtoMapper;


    public BillDto mapToDto(Bill bill){
//        return BillDto.builder()
//                .billNumber(bill.getBillNumber())
//                .creationDate(bill.getDateCreation().toString())
//                .billStatus(bill.getStatus().toString())
//                .billTotal(bill.getTotal().toString())
//                .billPaymentStatus(bill.getPaymentStatus().toString())
//                .clientEmail(bill.getClient().getEmail())
//                .clientName(bill.getClient().getName())
//                .dossierNumber(bill.getDossierLocation().getDossierNumber())
//                .billDetails(billDetailsDtoMapper.mapToDto(bill))
//                .build();
        return null;



                
    }
}
