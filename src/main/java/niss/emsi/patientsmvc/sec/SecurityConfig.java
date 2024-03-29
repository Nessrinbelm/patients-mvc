package niss.emsi.patientsmvc.sec;

import niss.emsi.patientsmvc.sec.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


//C'est une classe de configuration instancié au demarrage de l app
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       /* String encodedPWD=passwordEncoder.encode(  "1234");
        System.out.println(encodedPWD);
        //Les utilisateurs
       auth.inMemoryAuthentication()
                .withUser("user1").password(encodedPWD).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("1111")).roles("USER", "ADMIN")
                .and()
                .withUser("user2").password(passwordEncoder.encode("2345")).roles("USER");*/
      /* auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery ("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder (passwordEncoder);
       */
       //Quand l'utilisateur saisit son username et mdps Spring fait appel à cet Objet -> LoadUserbyUsername
        auth.userDetailsService(userDetailsService);





    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Retourner le formulaire par defaut
        http.formLogin() .loginPage("/login") .permitAll();
        http.authorizeRequests().antMatchers( "/").permitAll();

        //Si il n est pas admin il ne pourra pas y acceder aà delete et les autres
        http.authorizeRequests().antMatchers(  "/admin/**" ). hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers( "/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(  "/webjars/**").permitAll();
        //Chaque request necessite l'authentification
         http.authorizeRequests().anyRequest().authenticated();

         //Pour ces ressources tous le monde est permis
        //!!!!!! //http.authorizeRequests().antMatchers(  "/webjars/**").permitAll();


        http.exceptionHandling().accessDeniedPage("/403");

        //BASCULER VERS JDBC
        //CREATION DE TABLE DANS PHPMYADMIN




    }

}