package cristinapalmisani.BEU2W3L1.config;

import epicode.u5d9hw.entities.Author;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {

	private String mailgunApiKey;
	private String mailgunDomainname;

	public MailgunSender(@Value("${mailgun.apikey}") String mailgunApiKey,
	                     @Value("${mailgun.domainname}") String mailgunDomainName) {
		this.mailgunApiKey = mailgunApiKey;
		this.mailgunDomainname = mailgunDomainName;
	}

	public void sendRegistrationEmail(Author recipient) {
		// codice che invia l'email
		// Per inviare un'email devo mandare una richiesta http di tipo POST ai server mailgun
		// Per farlo uso la libreria unirest-java

		HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
				.basicAuth("api", this.mailgunApiKey)
				.queryString("from", "Riccardo Gulin <riccardo.gulin@gmail.com>")
				.queryString("to", recipient.getEmail())
				.queryString("subject", "Registrazione avvenuta con successo!")
				.queryString("text", "Complimenti " + recipient.getName() + " " + recipient.getSurname() + "  per esserti registrato!")
				.asJson();
	}
}
