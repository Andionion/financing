package cn.brody.financing.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author brody
 * @date 2021/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportTradeDTO implements Serializable {
    private static final long serialVersionUID = 4759476254308131524L;

    private LocalDate localDate;

    private Double amount;

}
