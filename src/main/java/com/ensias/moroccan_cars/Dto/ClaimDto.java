package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.Claim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDto {
    public ClaimDto(Claim claim){
        this(claim.getId(),claim.getSubject(),claim.getBody(),claim.getDate(),claim.getTreatmentDate(),new UserDto(claim.getUser()));
    }


    private int id;
    @NotNull
    @Size(min = 5)
    private String subject;
    @NotNull
    @Size(min = 10)
    private String body;
    private Date date;
    private Date treatmentDate;
    private UserDto user;
    public String getDate()  {
        if(date != null)
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(date);
        return  null;
    }
    public Claim asClaim(){
        return new Claim(id,subject,body,date,treatmentDate,user.User());
    }
}
