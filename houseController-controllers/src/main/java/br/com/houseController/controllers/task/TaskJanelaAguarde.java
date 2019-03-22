package br.com.houseController.controllers.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;


import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.concurrent.Task;

public class TaskJanelaAguarde implements Callable<Boolean> {
	public Usuario usuario;

public Boolean executa(Usuario usuario){	
		
			Task<Boolean> task = new Task<Boolean>(){

				@Override
				protected Boolean call() throws Exception {
					UsuarioService us = new UsuarioService();
					Thread.sleep(3000);
					return us.metodoTeste(usuario);			
				}
				
			};
			
		
			task.setOnRunning(e -> Aguarde2.mostrarJanelaAguarde());
			task.setOnSucceeded(e -> {
				try {
					Aguarde2.finalizarJanelaAguarde();			
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			task.setOnFailed(e -> System.out.println("Deu ruim"));
			
		try {
			new Thread(task).start();
			return task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return false;
		
		
	}

@Override
public Boolean call() throws Exception {
	
	Task<Boolean> task = new Task<Boolean>(){

		@Override
		protected Boolean call() throws Exception {
			UsuarioService us = new UsuarioService();
			Thread.sleep(3000);
			return us.metodoTeste(usuario);			
		}
		
	};
	

	task.setOnRunning(e -> Aguarde2.mostrarJanelaAguarde());
	task.setOnSucceeded(e -> {
		try {
			Aguarde2.finalizarJanelaAguarde();			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	});
	task.setOnFailed(e -> System.out.println("Deu ruim"));
	
try {
	new Thread(task).start();
	return task.get();
} catch (InterruptedException e) {
	e.printStackTrace();
} catch (ExecutionException e) {
	e.printStackTrace();
}
	
	return false; 
}
	
	

}
