package hr.markocaleta.prvih11;

import org.springframework.boot.SpringApplication;

public class TestCoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(Prvih11Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
