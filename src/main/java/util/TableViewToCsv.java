package util;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFTableStyleInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.simulador.es.dao.MruDAO;
import org.simulador.es.dao.SuperDAO;
import org.simulador.es.model.SuperModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.simulador.es.LoadWindow.primaryStage;

public class TableViewToCsv<T extends SuperDAO<? extends SuperModel>> {
    private T dao;

    public TableViewToCsv ( T dao ) {
        this.dao = dao;
    }

    public void guardarExcel () {
        FileChooser fileChooser = new FileChooser ( );
        fileChooser.setTitle ( "Save Excel File" );
        fileChooser.getExtensionFilters ( ).add ( new FileChooser.ExtensionFilter ( "Excel Files", "*.xlsx" ) );
        File file = fileChooser.showSaveDialog ( primaryStage );

        XSSFWorkbook workbook = new XSSFWorkbook ( );
        XSSFSheet sheet = workbook.createSheet ( "Velocidad Vs Tiempo" );
        Row headerRow = sheet.createRow ( 0 );
        headerRow.createCell ( 0 ).setCellValue ( "Tiempo" );

        if (dao instanceof MruDAO)
            headerRow.createCell ( 1 ).setCellValue ( "Desplazamiento" );
        else
            headerRow.createCell ( 1 ).setCellValue ( "Velocidad" );

        for (int i = 0; i < dao.getList ( ).size ( ); i++) {
            headerRow = sheet.createRow ( i + 1 );
            Cell cell = headerRow.createCell ( 0 );
            Cell cell1 = headerRow.createCell ( 1 );
            cell.setCellValue ( dao.getList ( ).get ( i ).getTiempo ( ) );
            if(dao instanceof  MruDAO)
                cell1.setCellValue ( dao.getList ().get ( i ).getDesplazamiento () );
            else
            cell1.setCellValue ( dao.getList ( ).get ( i ).getValue ( ) );

        }
        AreaReference areaReference = workbook.getCreationHelper ( ).createAreaReference (
                new CellReference ( 0, 0 ), new CellReference ( dao.getList ( ).size ( ),
                        1 )
        );
        XSSFTable table = sheet.createTable ( areaReference );

        establecerEstiloTabla ( table );

        try (FileOutputStream fileOut = new FileOutputStream ( file.getAbsolutePath ( ) )) {
            workbook.write ( fileOut );
        } catch (IOException e) {
            e.printStackTrace ( );
        }

        try {
            workbook.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }

    }
    void establecerEstiloTabla(XSSFTable table){
        table.setName ( "Gravitagora Simulator" );
        table.setDisplayName ( "Informacion" );
        table.getCTTable ( ).addNewTableStyleInfo ( );
        table.getCTTable ( ).getTableStyleInfo ( ).setName ( "TableStyleMedium2" );
        XSSFTableStyleInfo style = (XSSFTableStyleInfo) table.getStyle ( );
        style.setName ( "TableStyleMedium2" );
        style.setShowColumnStripes ( false );
        style.setShowRowStripes ( true );
        style.setFirstColumn ( false );
        style.setLastColumn ( false );
        style.setShowRowStripes ( true );
        style.setShowColumnStripes ( true );
    }
}
