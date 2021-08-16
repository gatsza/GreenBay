package org.gfa.greenbay.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  ModelMapper modelMapper;

  public ModelMapperConfiguration() {
    this.modelMapper = new ModelMapper();
  }

  @Bean
  public ModelMapper modelMapper() {
    return modelMapper;
  }
}
