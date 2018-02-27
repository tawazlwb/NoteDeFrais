package com.ingetis.ikheiry.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingetis.ikheiry.model.Client;

public interface IClientRepository extends JpaRepository<Client, Long>{

}
