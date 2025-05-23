package dev.snowdrop.command.cluster;

import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static dev.snowdrop.internal.controller.PackageController.initPackageController;
import static dev.snowdrop.internal.controller.PackageController.stopPackageController;

@CommandLine.Command(name = "stop-controller", description = "Stop the shared informer watching the Package resources")
public class StopController implements Callable<Integer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopController.class);
    private static final String KUBECONFIG_PATH = "/Users/cmoullia/code/ch007m/java-kind-client/kind1-kube.conf";

    @Override
    public Integer call() throws Exception {
        try {
            System.setProperty("KUBECONFIG", KUBECONFIG_PATH);
            KubernetesClient client = new DefaultKubernetesClient();

            LOGGER.info("Stopping the informer ...");
            stopPackageController(client);

            return 0;

        } catch (KubernetesClientException kce) {
            LOGGER.error("Kubernetes client error. Message: {}, Exception: {}",kce.getMessage(),kce);
            return 1;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 1;
        }
    }
}
