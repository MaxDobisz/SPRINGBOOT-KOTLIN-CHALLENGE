package com.example.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            authorizeRequests {
                authorize("/persons", hasRole("ADMIN"))
                authorize("/persons/filter**", hasAnyRole("USER","ADMIN"))
                authorize("/persons/{id}", hasRole("ADMIN"))
            }
            httpBasic {}
        }
        return http.build()
    }

    @Bean
    fun users(): UserDetailsService {
        val admin = User.builder()
                .username("admin")
                .password("{bcrypt}\$2a\$10\$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW") //123
                .roles("ADMIN")
                .build()
        val quest = User.builder()
                .username("quest")
                .password("{bcrypt}\$2a\$10\$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW") //123
                .roles("USER")
                .build()
        return InMemoryUserDetailsManager(quest,admin)
    }
}