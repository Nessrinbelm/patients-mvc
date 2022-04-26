package niss.emsi.patientsmvc.sec.service;

import niss.emsi.patientsmvc.sec.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private SecurityService securityService;
    //Injection via Constructeur ou Authowired


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Chercher l'utilisateur dans la base de données
        AppUser appUser=securityService.loadUserByUserName(username);
       //Collection de Role
        /*
        Collection<GrantedAuthority> authorities= new ArrayList();
        //Pour chaque role creer un SimpleGrantedauthority et l ajouter dans la collection
        appUser.getAppRoles().forEach(role-> {
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority (role.getRoleName());
            authorities.add (authority);
        });*/


        //En Utilisant API STREAM
        Collection<GrantedAuthority> authorities1=
                appUser.getAppRoles ()
                .stream ()
                .map (role->new SimpleGrantedAuthority (role.getRoleName()))
                .collect(Collectors.toList());

        //Pour chaque user apporter le nom le mdps la collecion de roles
        //Classe User de SpringSecurité
        //Transféré les données de AppUser vers User

        User user=new User (appUser.getUsername(), appUser.getPassword(),authorities1);

        return user;
    }
}
