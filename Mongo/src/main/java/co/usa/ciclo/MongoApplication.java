package co.usa.ciclo;

import co.usa.ciclo.Repository.crud.UserCrudRepository;
import co.usa.ciclo.Repository.crud.cloneCrudRepository;
import co.usa.ciclo.Repository.crud.cloneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@EntityScan(basePackages = ("co.usa.ciclo4.ciclo4.Modelo"))
@SpringBootApplication
public class MongoApplication {
        @Autowired
        private cloneCrudRepository cloneRepo;
        
        @Autowired
        private UserCrudRepository userRepo;
        
        
	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);
        }
        
        public void run(String... args) throws Exception{
        
                cloneRepo.deleteAll();
                userRepo.deleteAll();
            
        }

}
