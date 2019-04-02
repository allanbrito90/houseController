package br.com.houseController.main;


import br.com.houseController.persistence.ConnectionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe principal do programa
 * @author allan
 * @version 1.0
 *
 */
public class App extends Application{

    public static void main( String[] args )
    {
    	ConnectionFactory.criarConexao();    		
    	
//    	ReceitaService receitaService = new ReceitaService();
//    	ArrayList<Receita> receitas = receitaService.findAll();
//    	
//    	CategoriaService categoriaService = new CategoriaService();
//    	ArrayList<Categoria> categorias = categoriaService.findAll();
//    	
//    	DespesaVariavel despesaVariavel = new DespesaVariavel();
//    	despesaVariavel.setCategoria(categorias.get(0));
//    	despesaVariavel.setContaAtiva(EnumContaAtiva.ATIVO);
//    	despesaVariavel.setDescricaoDespesa("Conta de Água");
//    	despesaVariavel.setDtPagamento(LocalDateTime.now());
//    	despesaVariavel.setPago(false);
//    	despesaVariavel.setReceitaUtilizada(receitas.get(0));
//    	despesaVariavel.setValorDespesa(new BigDecimal(19.09));
//    	DespesaVariavelService despesaVariavelService = new DespesaVariavelService();
//    	despesaVariavelService.insert(despesaVariavel);
    	
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		try {
			
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
		stage.setTitle("Login");
		stage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root,350,180);
		stage.setScene(scene);
		stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void stop() throws Exception{
		System.out.println("Conexão Fechada");
		ConnectionFactory.fecharConexao();
	}
	
}
