package com.mundial.hub.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mundial.hub.model.*;
import com.mundial.hub.repository.UsuarioRepository;
import com.mundial.hub.repository.LaminaRepository;
@Configuration
public class DataLoader {

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository repo, PasswordEncoder passwordEncoder,
			LaminaRepository laminaRepo) {
		return args -> {

			if (repo.findByUsername("admin").isEmpty()) {
				Usuario admin = new Usuario();
				admin.setNombre("Administrador");
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("1234"));
				admin.setEmail("admin@mundial.com");
				admin.setRol("ADMIN");
				repo.save(admin);
				System.out.println("ADMIN CREADO");
			}

			if (repo.findByUsername("operador").isEmpty()) {
				Usuario operador = new Usuario();
				operador.setNombre("Operador");
				operador.setUsername("operador");
				operador.setPassword(passwordEncoder.encode("1234"));
				operador.setEmail("operador@mundial.com");
				operador.setRol("OPERADOR");
				repo.save(operador);
				System.out.println("OPERADOR CREADO");
			}

			if (repo.findByUsername("usuario").isEmpty()) {
				Usuario usuario = new Usuario();
				usuario.setNombre("Usuario de Prueba");
				usuario.setUsername("usuario");
				usuario.setPassword(passwordEncoder.encode("1234"));
				usuario.setEmail("usuario@mundial.com");
				usuario.setRol("USUARIO");
				repo.save(usuario);
				System.out.println("USUARIO CREADO");
			}

			if (laminaRepo.count() == 0) {

				String bgArg = "74ACDF";
				laminaRepo.save(new Lamina("Lionel Messi", "Argentina",
						"https://ui-avatars.com/api/?name=Lionel+Messi&background=" + bgArg + "&color=fff&size=200",
						15)); 
				laminaRepo.save(new Lamina("Emiliano Martínez", "Argentina",
						"https://ui-avatars.com/api/?name=Emiliano+Martinez&background=" + bgArg
								+ "&color=fff&size=200",
						8));
				laminaRepo.save(new Lamina("Julián Álvarez", "Argentina",
						"https://ui-avatars.com/api/?name=Julian+Alvarez&background=" + bgArg + "&color=fff&size=200",
						3));
				laminaRepo.save(new Lamina("Enzo Fernández", "Argentina",
						"https://ui-avatars.com/api/?name=Enzo+Fernandez&background=" + bgArg + "&color=fff&size=200",
						3));
				laminaRepo.save(new Lamina("Alexis Mac Allister", "Argentina",
						"https://ui-avatars.com/api/?name=Alexis+Mac+Allister&background=" + bgArg
								+ "&color=fff&size=200",
						3));

				String bgBra = "FFDF00";
				laminaRepo.save(new Lamina("Vinícius Jr", "Brasil",
						"https://ui-avatars.com/api/?name=Vinicius+Jr&background=" + bgBra + "&color=000&size=200", 8));
				laminaRepo.save(new Lamina("Rodrygo", "Brasil",
						"https://ui-avatars.com/api/?name=Rodrygo&background=" + bgBra + "&color=000&size=200", 3));
				laminaRepo.save(new Lamina("Alisson Becker", "Brasil",
						"https://ui-avatars.com/api/?name=Alisson+Becker&background=" + bgBra + "&color=000&size=200",
						3));
				laminaRepo.save(new Lamina("Casemiro", "Brasil",
						"https://ui-avatars.com/api/?name=Casemiro&background=" + bgBra + "&color=000&size=200", 3));
				laminaRepo.save(new Lamina("Marquinhos", "Brasil",
						"https://ui-avatars.com/api/?name=Marquinhos&background=" + bgBra + "&color=000&size=200", 3));

				// COLOMBIA
				String bgCol = "FCD116";
				laminaRepo.save(new Lamina("James Rodríguez", "Colombia",
						"https://ui-avatars.com/api/?name=James+Rodriguez&background=" + bgCol + "&color=000&size=200",
						8));
				laminaRepo.save(new Lamina("Luis Díaz", "Colombia",
						"https://ui-avatars.com/api/?name=Luis+Diaz&background=" + bgCol + "&color=000&size=200", 8));
				laminaRepo.save(new Lamina("Camilo Vargas", "Colombia",
						"https://ui-avatars.com/api/?name=Camilo+Vargas&background=" + bgCol + "&color=000&size=200",
						3));
				laminaRepo.save(new Lamina("Jhon Arias", "Colombia",
						"https://ui-avatars.com/api/?name=Jhon+Arias&background=" + bgCol + "&color=000&size=200", 3));
				laminaRepo.save(new Lamina("Richard Ríos", "Colombia",
						"https://ui-avatars.com/api/?name=Richard+Rios&background=" + bgCol + "&color=000&size=200",
						3));

				String bgFra = "002395";
				laminaRepo.save(new Lamina("Kylian Mbappé", "Francia",
						"https://ui-avatars.com/api/?name=Kylian+Mbappe&background=" + bgFra + "&color=fff&size=200",
						15)); // 👑 Caro
				laminaRepo.save(new Lamina("Antoine Griezmann", "Francia",
						"https://ui-avatars.com/api/?name=Antoine+Griezmann&background=" + bgFra
								+ "&color=fff&size=200",
						3));
				laminaRepo.save(new Lamina("Aurélien Tchouaméni", "Francia",
						"https://ui-avatars.com/api/?name=Aurelien+Tchouameni&background=" + bgFra
								+ "&color=fff&size=200",
						3));
				laminaRepo.save(new Lamina("William Saliba", "Francia",
						"https://ui-avatars.com/api/?name=William+Saliba&background=" + bgFra + "&color=fff&size=200",
						3));
				laminaRepo.save(new Lamina("Mike Maignan", "Francia",
						"https://ui-avatars.com/api/?name=Mike+Maignan&background=" + bgFra + "&color=fff&size=200",
						3));

				String bgEsp = "AA151B";
				laminaRepo.save(new Lamina("Rodri", "España",
						"https://ui-avatars.com/api/?name=Rodri&background=" + bgEsp + "&color=F1BF00&size=200", 8));
				laminaRepo.save(new Lamina("Lamine Yamal", "España",
						"https://ui-avatars.com/api/?name=Lamine+Yamal&background=" + bgEsp + "&color=F1BF00&size=200",
						8));
				laminaRepo.save(new Lamina("Pedri", "España",
						"https://ui-avatars.com/api/?name=Pedri&background=" + bgEsp + "&color=F1BF00&size=200", 3));
				laminaRepo.save(new Lamina("Dani Carvajal", "España",
						"https://ui-avatars.com/api/?name=Dani+Carvajal&background=" + bgEsp + "&color=F1BF00&size=200",
						3));
				laminaRepo.save(new Lamina("Unai Simón", "España",
						"https://ui-avatars.com/api/?name=Unai+Simon&background=" + bgEsp + "&color=F1BF00&size=200",
						3));

				String bgPor = "FF0000";
				laminaRepo.save(new Lamina("Cristiano Ronaldo", "Portugal",
						"https://ui-avatars.com/api/?name=Cristiano+Ronaldo&background=" + bgPor
								+ "&color=006600&size=200",
						15)); // 👑 Caro
				laminaRepo.save(new Lamina("Bruno Fernandes", "Portugal",
						"https://ui-avatars.com/api/?name=Bruno+Fernandes&background=" + bgPor
								+ "&color=006600&size=200",
						3));
				laminaRepo.save(new Lamina("Bernardo Silva", "Portugal",
						"https://ui-avatars.com/api/?name=Bernardo+Silva&background=" + bgPor
								+ "&color=006600&size=200",
						3));
				laminaRepo.save(new Lamina("Rúben Dias", "Portugal",
						"https://ui-avatars.com/api/?name=Ruben+Dias&background=" + bgPor + "&color=006600&size=200",
						3));
				laminaRepo.save(new Lamina("Rafael Leão", "Portugal",
						"https://ui-avatars.com/api/?name=Rafael+Leao&background=" + bgPor + "&color=006600&size=200",
						3));

				System.out.println("LÁMINAS CON PRECIOS DINÁMICOS CONFIGURADAS");

				System.out.println("50 LÁMINAS CREADAS EXITOSAMENTE");
			}
		};
	}
}