package com.banxia.api;


import com.banxia.api.dto.CreatePayRequestDTO;
import com.banxia.api.response.Response;

import javax.servlet.http.HttpServletRequest;

public interface IPayService {

    Response<String> createPayOrder(CreatePayRequestDTO createPayRequestDTO);
}
