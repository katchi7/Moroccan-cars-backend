package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.RentRequest;
import com.ensias.moroccan_cars.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestDto {


    public RentRequestDto(RentRequest rentRequest){
        this(rentRequest.getId(),rentRequest.getDate(),rentRequest.getDateStart(),rentRequest.getDateEnd(),new UserDto(rentRequest.getUser()), rentRequest.getVehicule().getId(),rentRequest.getStatus());
    }

    private int id;
    private Date date;

    @NotNull
    private Date dateStart;
    @NotNull
    private Date dateEnd;

    private UserDto user;

    @Min(1)
    private int vehicule_id;

    private Status status;

    public boolean validDates() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return dateStart.after(df.parse(df.format(date))) && dateEnd.after(dateStart);
    }

    public RentRequest asRentRequest(){
        VehiculeDto vehicule = new VehiculeDto();
        vehicule.setId(vehicule_id);
        return new RentRequest(id,date,dateStart,dateEnd,user.User(),vehicule.asVehicule(),status);
    }

    public void setDateStart(String date) throws ParseException {
        this.dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public void setDateEnd(String dateEnd) throws ParseException {
        this.dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
    }
}
