package br.com.amparoedu.backend.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

// Faz a comunicação com o Supabase via REST API para sincronizar os dados

public class SupabaseClient {
    private static final String URL = "https://vmczstzsvoecrqnlaade.supabase.co/rest/v1/";
    private static final String KEY = "sb_publishable_HeoMaB5-z_KKY7GWREXyrg_JO9Cg8E9";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public boolean enviarParaNuvem(String tabela, Object objeto) {
        try {
            String json = gson.toJson(objeto);
            String urlFinal = URL + tabela;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .header("apikey", KEY)
                .header("Authorization", "Bearer " + KEY)
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates") // insere ou atualiza
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (Exception e) {
            return false;
        }
    }

    public String buscarDaNuvem(String tabela) {
    try {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL + tabela))
            .header("apikey", KEY)
            .header("Authorization", "Bearer " + KEY)
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

}