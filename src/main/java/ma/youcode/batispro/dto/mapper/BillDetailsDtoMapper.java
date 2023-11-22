package ma.youcode.batispro.dto.mapper;


import ma.youcode.batispro.domain.entity.Bill;
import ma.youcode.batispro.domain.entity.BillDetails;
import ma.youcode.batispro.dto.BillDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class BillDetailsDtoMapper {

    public BillDetailsDto mapToDto(Bill bill){
        return BillDetailsDto.builder()
                .dossierNumber(bill.getBillDetails().getBill().getDossierLocation().getDossierNumber())
                .equipmentPrice(bill.getBillDetails().getPriceUnit().toString())
                .equipmentQuantity(bill.getBillDetails().getQuantity().toString())
                .totalPrice(bill.getBillDetails().getTotalPrice().toString())
                .build();
    }
}
