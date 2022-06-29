package com.blusalt.assessment.billingworkerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Fund implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long customerId;

    String amount;

    String transactionId;

    String status;

    Date createdOn;
}

