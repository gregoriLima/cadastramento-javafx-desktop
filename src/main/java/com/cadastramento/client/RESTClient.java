package com.cadastramento.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.cadastramento.model.PagedPfResult;
import com.cadastramento.model.PessoaFisica;
import com.cadastramento.model.Usuario;
import com.cadastramento.singleton.OkHttpSingleton;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RESTClient {

    private static String jwt = "empty";

    private static boolean isUserAuthenticated = false;

    private static final String WS_URI = "http://desafiofsbr.ddns.net:8080";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient CLIENT = OkHttpSingleton.getClient();

    public static <Type> Type salvaPessoaFisica(PessoaFisica pf) {

        PessoaFisica pfResult = null;
        String resultMessage = " ";

        RequestBody body = RequestBody.create(new Gson().toJson(pf), JSON);

        Request request = new Request.Builder().url(WS_URI + "/cadastro").post(body)
                .addHeader("Authorization", "Bearer " + jwt).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {
                pfResult = new Gson().fromJson(response.body().string(), PessoaFisica.class);

            } else if (response.code() == 404)
                resultMessage = "404 not found.";
            else {
//                System.out.println(response.body().string());
                Map responseMap = new Gson().fromJson(response.body().string(), Map.class);
                String message = ((String) responseMap.get("message"));
                if (message.contains(" for "))
                message = message.split(" for ")[0];
                resultMessage = "Erro ao salvar. Msg: " + message;

            }

        } catch (IOException e) {
            System.out.println("Método salvaPessoaFisica(). Falha no request " + e.getMessage());
            resultMessage = "Erro ao realizar a consulta. Verifique a conexão.";
        }

        if (pfResult == null)
            return (Type) resultMessage;

        return (Type) pfResult;

    }


    public static <Type> Type atualizarPessoaFisica(PessoaFisica pf) {

        PessoaFisica pfResult = null;
        String resultMessage = " ";

        RequestBody body = RequestBody.create(new Gson().toJson(pf), JSON);

        Request request = new Request.Builder().url(WS_URI + "/cadastro/" + pf.getId()).put(body)
                .addHeader("Authorization", "Bearer " + jwt).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {

                pfResult = new Gson().fromJson(response.body().string(), PessoaFisica.class);

            } else if (response.code() == 404)
                resultMessage = "404 not found.";
            else {

                Map responseMap = new Gson().fromJson(response.body().string(), Map.class);
                String message = ((String) responseMap.get("message"));
                if (message.contains(" for "))
                    ;
                message = message.split(" for ")[0];
                resultMessage = "Erro ao salvar. Err: " + responseMap.get("error") + ", Msg: " + message;

            }

        } catch (IOException e) {
            System.out.println("Método salvaPessoaFisica(). Falha no request " + e.getMessage());
            resultMessage = "Erro ao realizar a consulta. Verifique a conexão.";
        }

        if (pfResult == null)
            return (Type) resultMessage;

        return (Type) pfResult;

    }

    public static boolean autentica(Usuario usuario) {

        RequestBody body = RequestBody.create(usuario.toJson(), JSON);

        Request request = new Request.Builder().url(WS_URI + "/auth").post(body).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {
                Map<String, String> resp = new Gson().fromJson(response.body().string(), Map.class);
                jwt = resp.get("token");

                isUserAuthenticated = true;

            } else {
                isUserAuthenticated = false;
            }

        } catch (IOException e) {
            System.out.println("Método autentica(). Falha no request " + e.getMessage());

        }

        return isUserAuthenticated;
    }

    // Check the status of RESTful WebService
    public static boolean checkWS() {
        Boolean stateOfWS = false;

        Request request = new Request.Builder().url(WS_URI + "/actuator/health").method("GET", null).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {
                Map<String, String> resp = new Gson().fromJson(response.body().string(), Map.class);
                stateOfWS = resp.containsKey("status") ? resp.get("status").equals("UP") : false;

            }

        } catch (IOException e) {
            System.out.println("Método checkWS(). Falha no request " + e.getMessage());
        }

        return stateOfWS;
    }

    public static List<String> getEstados() {

        Request request = new Request.Builder().url(WS_URI + "/cadastro/estados").method("GET", null).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {
                List<String> resp = new Gson().fromJson(response.body().string(), List.class);

                return resp;
            }

        } catch (Exception e) {
            System.out.println("Método getEstados(). Falha no request " + e.getMessage());
        }

        return null;
    }

    public static ArrayList<PessoaFisica> buscarTodosRegistros() {

        Request request = new Request.Builder().url(WS_URI + "/cadastro").method("GET", null)
                .addHeader("Authorization", "Bearer " + jwt).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {

                PagedPfResult resp = new Gson().fromJson(response.body().string(), PagedPfResult.class);

                return resp.getContent();

            } else {
                System.out.println(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Método getEstados(). Falha no request " + e.getMessage());
        }

        return null;
    }

    public static <Type> Type buscarPorCPF(String cpf) {

        ArrayList<PessoaFisica> pfResult = null;
        String resultMessage = " ";

        Request request = new Request.Builder().url(WS_URI + "/cadastro/cpf/" + cpf).method("GET", null)
                .addHeader("Authorization", "Bearer " + jwt).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {

                pfResult = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<PessoaFisica>>() {
                }.getType());

                if(pfResult.isEmpty()) resultMessage = "Nenhum registro encontrado";

            } else if (response.code() == 404)
                resultMessage = "404 not found.";
            else {
                Map responseMap = new Gson().fromJson(response.body().string(), Map.class);
                String message = ((String) responseMap.get("message"));
                if (message.contains(" for "))
                    message = message.split(" for ")[0];
                resultMessage = "Erro ao salvar. Err: " + responseMap.get("error") + ", Msg: " + message;
            }

        } catch (Exception e) {
            System.out.println("Método buscarPorCPF(). Falha no request " + e.getMessage());
            resultMessage = "Erro ao realizar a consulta. Verifique a conexão.";
        }

        if (pfResult == null || pfResult.isEmpty())
            return (Type) resultMessage;

        return (Type) pfResult;
    }

    public static <Type> Type buscarPorNome(String nome) {

        ArrayList<PessoaFisica> pfResult = null;
        String resultMessage = " ";

        Request request = new Request.Builder().url(WS_URI + "/cadastro/nome/" + nome).method("GET", null)
                .addHeader("Authorization", "Bearer " + jwt).build();

        try (Response response = CLIENT.newCall(request).execute();) {

            if (response.isSuccessful()) {

                pfResult = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<PessoaFisica>>() {
                }.getType());

                if(pfResult.isEmpty()) resultMessage = "Nenhum registro encontrado";

            } else if (response.code() == 404)
                resultMessage = "404 not found.";
            else {
                Map responseMap = new Gson().fromJson(response.body().string(), Map.class);
                String message = ((String) responseMap.get("message"));
                if (message.contains(" for "))
                    message = message.split(" for ")[0];
                resultMessage = "Erro ao salvar. Err: " + responseMap.get("error") + ", Msg: " + message;
            }

        } catch (Exception e) {

            System.out.println("Método buscarPorNome(). Falha no request " + e.getMessage());
            resultMessage = "Erro ao realizar a consulta. Verifique a conexão.";
        }

        if (pfResult == null  || pfResult.isEmpty())
            return (Type) resultMessage;

        return (Type) pfResult;
    }


    public static boolean excluirPorId(int id) {

        Request request = new Request.Builder().url(WS_URI + "/cadastro/" + id).method("DELETE", null)
                .addHeader("Authorization", "Bearer " + jwt).build();
        try (Response response = CLIENT.newCall(request).execute();) {
            if (response.isSuccessful())
                return true;

        } catch (Exception e) {
            System.out.println("Método excluirPorId(). Falha no request " + e.getMessage());
        }

        return false;

    }

}
