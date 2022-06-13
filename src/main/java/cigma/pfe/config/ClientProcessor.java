package cigma.pfe.config;

import cigma.pfe.models.Client;
import org.springframework.batch.item.ItemProcessor;

public class ClientProcessor implements ItemProcessor<Client, Client> {

    @Override
    public Client process(Client client) throws Exception {

        System.out.println(client);
        return client;
    }
}
