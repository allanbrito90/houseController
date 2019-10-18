package br.com.houseController.controllers.dialogs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.FutureTask;

import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.Controller;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.despesas.RelacaoDespesaReceita;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.service.Despesa.RelacaoDespesaReceitaService;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class AdicionarPagamentoController extends Controller implements Initializable{
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private JFXComboBox<Receita> jcbReceita;
	
	@FXML
	private Label jlValorRestanteReceita;
	
	@FXML
	private VBox vbValor;
	
	@FXML
	private NumberTextField jntf;
	
	@FXML
	private AnchorPane jbSalvar;
	
	
	private ReceitaService receitaService = new ReceitaService();
	private BigDecimal valorRestanteDespesa;
	private RelacaoDespesaReceita relacaoDespesaReceita = new RelacaoDespesaReceita();
	private RelacaoDespesaReceitaService relacaoDespesaReceitaService = new RelacaoDespesaReceitaService();
	private BigDecimal valorRestante = BigDecimal.ZERO;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Receita> listReceita = receitaService.findAll();
		for(Receita receita : listReceita){
			jcbReceita.getItems().add(receita);
		}
		
		jntf = new NumberTextField();
		
		Platform.runLater(()->{
			vbValor.setVisible(false);
			vbValor.getChildren().add(jntf);
		});
		
		
		
		
		
		jcbReceita.setCellFactory(new Callback<ListView<Receita>, ListCell<Receita>>() {
			
			@Override
			public ListCell<Receita> call(ListView<Receita> param) {
				final ListCell<Receita> cell = new ListCell<Receita>(){
					
					@Override
					public void updateItem(Receita item, boolean empty){
						super.updateItem(item, empty);
						
						if(item != null){
							setText(item.getDescricaoPagamento() + " - R$" + item.getValor().setScale(2, RoundingMode.HALF_EVEN));
						}else{
							setText("");
						}
						
					}
				};
				return cell;
			}
		});
		
		jcbReceita.setConverter(new StringConverter<Receita>() {			
			
			@Override
			public String toString(Receita object) {
				if(object == null) {
					return null;
				}else {
					return object.getDescricaoPagamento() + " - R$" + object.getValor().setScale(2, RoundingMode.HALF_EVEN);
				}
			}
			
			@Override
			public Receita fromString(String string) {
				return null;
			}
		});
		
		jcbReceita.valueProperty().addListener((obs,oldV,newV)->{
			if(newV != null){
				jbSalvar.setVisible(true);
				ArrayList<RelacaoDespesaReceita> listRelacaoDespesaReceita = relacaoDespesaReceitaService.findAllByReceita(jcbReceita.getSelectionModel().getSelectedItem().getId());
				valorRestante = jcbReceita.getSelectionModel().getSelectedItem().getValor().setScale(2,RoundingMode.HALF_EVEN);
				for(RelacaoDespesaReceita relacaoDespesaReceita : listRelacaoDespesaReceita){
					valorRestante = valorRestante.subtract(relacaoDespesaReceita.getValor());
				}
				jlValorRestanteReceita.setText(valorRestante.setScale(2,RoundingMode.HALF_EVEN).toString());
				vbValor.setVisible(true);
			}
		});
		
		
		
	}

	public AdicionarPagamentoController() {
		super();
	}
	
	public void handleSalvar(){
		if(jntf.getValor().compareTo(valorRestante)==1 || jntf.getValor().compareTo(valorRestanteDespesa)==1){
			ScreenUtils.janelaInformação(spDialog, "Valor excedente", "Valor maior que o permitido", "Ok");
			return;
		}
		relacaoDespesaReceita.setIdReceita(jcbReceita.getSelectionModel().getSelectedItem().getId());
		relacaoDespesaReceita.setValor(jntf.getValor());
		Stage stage = (Stage) spDialog.getScene().getWindow();
		stage.close();
	}
	
	public void handleLimpar(){
		Stage stage = (Stage) spDialog.getScene().getWindow();
		stage.close();
	}
	
	public RelacaoDespesaReceita getRelacaoDespesaReceita() {
		return relacaoDespesaReceita;
	}
	
	public void setRelacaoDespesaReceita(RelacaoDespesaReceita relacaoDespesaReceita) {
		this.relacaoDespesaReceita = relacaoDespesaReceita;
	}

	public BigDecimal getValorRestanteDespesa() {
		return valorRestanteDespesa;
	}

	public void setValorRestanteDespesa(BigDecimal valorRestanteDespesa) {
		this.valorRestanteDespesa = valorRestanteDespesa;
	}	
	
	

}
