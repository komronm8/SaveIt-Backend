package com.example.SaveItBackend.Security;

import com.example.SaveItBackend.Customer.Customer;
import com.example.SaveItBackend.Customer.CustomerRepository;
import com.example.SaveItBackend.Store.Store;
import com.example.SaveItBackend.Store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService{

    private final CustomerRepository customerRepository;

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
        Optional<Store> storeOptional = storeRepository.findStoreByEmail(email);
        if(customerOptional.isPresent()){
            return new User(
                    customerOptional.get().getEmail(),
                    customerOptional.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        }
        else if(storeOptional.isPresent()){
            return new User(
                    storeOptional.get().getEmail(),
                    storeOptional.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        }
        throw new IllegalStateException("The User does not exist");
    }
}
