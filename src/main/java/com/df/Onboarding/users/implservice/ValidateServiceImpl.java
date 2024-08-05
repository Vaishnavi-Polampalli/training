package com.df.Onboarding.users.implservice;

import com.df.Onboarding.phone.model.PhoneNumber;
import com.df.Onboarding.users.model.IdempotencyKey;
import com.df.Onboarding.users.model.ResultList;
import com.df.Onboarding.users.repo.IdempotencyKeyRepository;
import com.df.Onboarding.users.repo.RepoManager;
import com.df.Onboarding.users.exceptions.InvalidUserNameException;
import com.df.Onboarding.users.model.ResultMessage;
import com.df.Onboarding.users.model.Users;
import com.df.Onboarding.users.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Component
public class ValidateServiceImpl implements Validate {

    @Autowired
    RepoManager repoManager;
    @Autowired
    IdempotencyKeyRepository idempotencyKeyRepository;

    @Override
    public void checkValidUser(Users user, ResultMessage resultMessage, String idempotencyKey) {
        System.out.println("User == " +user);
        if(user.getFirstName()==null || user.getLastName()==null){
            throw new InvalidUserNameException("Invalid User Name");
        }
        String firstName= user.getFirstName().toLowerCase();
        String lastName= user.getLastName().toLowerCase();

        int firstNameLength=firstName.length();
        int lastnameLength=lastName.length();

        if(firstNameLength>0 && (firstName.charAt(0)=='v'||firstName.charAt(0)=='k')){
            invalidateUser(resultMessage);
        }else if((firstNameLength>0&&(firstName.charAt(firstNameLength-1)=='a'||firstName.charAt(firstNameLength-1)=='i')) || (lastnameLength>0&&(lastName.charAt(lastnameLength-1)=='a'||lastName.charAt(lastnameLength-1)=='i'))){
            invalidateUser(resultMessage);
        }else{
            idempotencyCheck(user, resultMessage,idempotencyKey);
        }

    }
    public void invalidateUser(ResultMessage resultMessage){

        resultMessage.setSuccess(false);
        resultMessage.setMessage("Invalid User");
    }

    public void idempotencyCheck(Users user, ResultMessage resultMessage, String idempotencyKey){
        IdempotencyKey savedKey = idempotencyKeyRepository.findById(idempotencyKey).orElse(null);

        if (savedKey != null) {
            if (savedKey.getExpiryDate().isBefore(LocalDateTime.now())) {
                //here already expired now delete this key so that when ever next time it comes with same key it will do necessary changes or updates
                idempotencyKeyRepository.delete(savedKey);
            }
                resultMessage.setSuccess(true);
                resultMessage.setMessage(savedKey.getResponse());
            return;
        }



        IdempotencyKey newKey = new IdempotencyKey();
        newKey.setKey(idempotencyKey);
        newKey.setResponse("Congratulations! "+user.getFirstName()+" "+user.getLastName()+". You are now a active user. ");
        newKey.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24-hour expiration
        idempotencyKeyRepository.save(newKey);

        //saving the User
        saveUser(user,resultMessage,newKey);

    }

    public void saveUser(Users user, ResultMessage resultMessage, IdempotencyKey savedKey){
        if(!repoManager.existsById(user.getId())){
            //make change only if id is not present
            setDafaults(user);
            repoManager.save(user);
            resultMessage.setSuccess(true);
            resultMessage.setMessage("Congratulations! "+user.getFirstName()+" "+user.getLastName()+". You are now a active user. ");

        }else{
            resultMessage.setSuccess(false);
            resultMessage.setMessage("A user already exist with same id choose a differnet id");
            idempotencyKeyRepository.delete(savedKey);
        }


    }

    private void setDafaults(Users user) {
        if(user.getCreatedBy()==null){
            user.setCreatedBy("Unknown User");
        }
        if(user.getCreatedTime()==null){
            user.setCreatedTime(LocalDateTime.now());
        }
    }

    public PhoneNumber restTemplateUsage(Users firstName) {
        //logic to find users with the firstName
        PhoneNumber phoneNumber =null;
        Users user = repoManager.findTop1ByFirstNameAndLastName(firstName.getFirstName(),firstName.getLastName());
        if(user==null){
            phoneNumber = new PhoneNumber();
            phoneNumber.setPhoneNumber("Not found");
            return phoneNumber;
        }
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/phone/?id=" + user.getId();
        return restTemplate.getForObject(url, PhoneNumber.class);
    }

    public ResultList getAllUsersData(){
        ResultList currentList = new ResultList();
        currentList.addAll(repoManager.findAll());
        return currentList;
    }


}
