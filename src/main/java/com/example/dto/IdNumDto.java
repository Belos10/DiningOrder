package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class IdNumDto {
    @JsonFormat(with =JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    List<List<BigInteger>> idNumMap = new ArrayList<>();
}

