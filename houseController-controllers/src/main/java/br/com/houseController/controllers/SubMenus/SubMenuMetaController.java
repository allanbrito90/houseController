package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import br.com.houseController.meta.Meta.MetaService;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import br.com.houseController.model.meta.Meta;
import br.com.houseController.model.usuario.UsuarioLogado;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class SubMenuMetaController extends Controller implements Initializable{
	
	@FXML
	private VBox vbMenu;
	
	@FXML
	private TableView<Meta> jtvMetas;
	
	@FXML
	private TableColumn<Meta, String> colMeta;
	
	@FXML
	private TableColumn<Meta, String> colData;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colMeta.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Meta,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Meta, String> param) {
				return new SimpleStringProperty(param.getValue().getDtConclusao().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			}
		});
		
		jtvMetas.setRowFactory(tv -> new TableRow<Meta>() {
		    @Override
		    public void updateItem(Meta item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.isCumprido()) {
		            setStyle("-fx-background-color: #24AD09;");
		        } else {
		            setStyle("");
		        }
		    }
		});
		
		UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
		MetaService metaService = new MetaService();
		ArrayList<Meta> metas = metaService.findMetabyUsuario(usuarioLogado.getUsuario());
		
		for (Meta meta : metas){
			jtvMetas.getItems().add(meta);
		}
		
		Platform.runLater(() -> {
			
			// Criando os Blocos nos HBoxes
			List<HBox> hBoxes = criaBlocosSubMenus(
					new BlocoSubMenu("Nova Meta", "images/nova_meta.png", "fxml/Meta/NovaMeta.fxml"), 
					new BlocoSubMenu("Alterar Meta", "images/alterar_meta.png", "fxml/Meta/AlterarMeta.fxml")
					);

			// Adicionando os HBoxes prontos no VBox
			for (HBox hBox : hBoxes) {
				vbMenu.getChildren().add(hBox);
			}
		});
	}
	

}
