Steps to deploy Dapr secrets management with Azure Key Vault as secret store:

- These steps assume that the cluster is set up like described in `steps-to-set-up-cluster.md`.
- These steps assume that the Dapr is set up like described in `deploy-distributed-apps-utilizing-dapr.md`.

1. Add details about Azure Key Vault to the Kubernetes secret store:
We do this, so that the details can be left out of the Kubernetes manifest file.

The Azure Key Vault details that we need are: 
- [your-key-vault-name]
- [your-service-principal-tenant-id]
- [your-service-principal-app-id]
- [your-pfx-certificate-file-fully-qualified-local-path]

Gather these values for your existing Azure Key Vault or create a new one. Also see: https://docs.dapr.io/reference/components-reference/supported-secret-stores/azure-keyvault/#setup-key-vault-and-service-principal

These values can be added to the Kubernetes secret store by using kubectl:
`kubectl create secret generic key-vault-name-secret-name --from-literal=key-vault-name-secret-key=[your-key-vault-name]`

`kubectl create secret generic service-principal-tenant-id-secret-name --from-literal=service-principal-tenant-id-secret-key=[your-service-principal-tenant-id]`

`kubectl create secret generic service-principal-app-id-secret-name --from-literal=service-principal-app-id-secret-key=[your-service-principal-app-id]`

`kubectl create secret generic service-principal-certificate-secret-name --from-file=service-principal-certificate-secret-key=[your-pfx-certificate-file-fully-qualified-local-path]`

2. Deploy the Dapr secret store component: `kubectl apply -f dapr-component-secret-store-azure-key-vault.yaml`. The result should be similar to:
```
component.dapr.io/vault created
```

3. Enter the secrets for app-a and app-b in the Azure Key Vault.
name: app-name-app-a; value: application 1
name: app-name-app-b; value: application 2

The end result should be a Dapr component on your local Docker Desktop Kubernetes cluster.

You can verify that the set up is working by going to http://localhost:55555/app-a/who2 and/or http://localhost:55555/app-b/who2. The responses should contain requestFrom and responseFrom properties with values you assigned in the Azure Key Vault.
