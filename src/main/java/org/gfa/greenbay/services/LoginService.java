package org.gfa.greenbay.services;

import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.dtos.LoginResponseDTO;

public interface LoginService {

  LoginResponseDTO login(LoginRequestDTO loginRequest);
}
