package data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.siat.pasajeros.data.mapper.JSONMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DataTransformer {
	
	private Context context;
	private JSONMapper json_mapper;
	public static final String FILE_NAME = "datos_exportados.json";
	
	/**public static final String HOST_NAME = "fundacion.satoriwd.com";
	**public static final String HOST_NAME = "fundacion.satoriwd.com";
    **public static final String HOST_NAME ="pixopruebas.ddns.net/fundacion/server.php";
    **/	 
	public static final String HOST_NAME = "santandercalidad.com/fundacion/public/";
	
	public DataTransformer( Context context ){
		this.context = context;
	}
	
	public String exportJSONFile() throws JSONException, IOException{
		json_mapper = new JSONMapper(context);
		JSONObject json_data = new JSONObject();
		JSONArray json_data_array = json_mapper.readData();
		if( json_data_array == null ){
			return null;
		}
		json_data.accumulate("data", json_data_array);
		json_data.accumulate("device", Build.SERIAL);
		File external_storage_dir = Environment.getExternalStorageDirectory();
		File export_file = new File(external_storage_dir , FILE_NAME);
		if( export_file.exists() ) export_file.delete();
		FileOutputStream f_out = new FileOutputStream(export_file);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(f_out);
        outputStreamWriter.write(json_data.toString());
        outputStreamWriter.close();
        return export_file.getAbsolutePath();
	}
	
	public void importJSONFile(){	
	}	
}
