package niss.emsi.patientsmvc.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//C'est une classe de configuration instancié au demarrage de l app
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder=passwordEncoder();
        String encodedPWD=passwordEncoder.encode(  "1234");
        System.out.println(encodedPWD);
        //Les utilisateurs
        auth.inMemoryAuthentication()
                .withUser("user1").password(encodedPWD).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("1111")).roles("USER", "ADMIN")
                .and()
                .withUser("user2").password(passwordEncoder.encode("2345")).roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        //Retourner le formulaire par defaut
        http.formLogin();
        http.authorizeRequests().antMatchers( "/").permitAll();

        //Si il n est pas admin il ne pourra pas y acceder aà delete et les autres
        http.authorizeRequests().antMatchers(  "/admin/**" ). hasRole("ADMIN");

        http.authorizeRequests().antMatchers( "/user/**").hasRole("USER");


        //Chaque request necessite l'authentification
         http.authorizeRequests().anyRequest().authenticated();

        http.exceptionHandling().accessDeniedPage("/403"); */

        //BASCULER VERS JDBC
        //CREATION DE TABLE DANS PHPMYADMIN




    }

    //Bean signife au demarrage creer un objet de type passwordencoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}