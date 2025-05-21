package EMS.User_Service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import EMS.User_Service.Entity.User;
import EMS.User_Service.Entity.UserPrinciple;
import EMS.User_Service.Repository.UserRepo;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    public UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepo.findByUserName(username);
        if(user==null){
            System.out.println("user not found - " + username);
            throw new UsernameNotFoundException(username);
        }
        return new UserPrinciple(user);
    }
}
