Steps to deploy distributed apps which are utilizing Dapr:

- These steps assume that the cluster is set up like described in `steps-to-set-up-cluster.md`.
    - Steps 2a and 3a could be skipped if you've done all the steps from `steps-to-set-up-cluster.md`. There is no harm in redoing these steps.

1. Install Dapr on your Kubernetes cluster: `dapr init -k` (Prerequisite is that you've the Dapr CLI installed on your machine.) The result should be similar to:
```
⌛  Making the jump to hyperspace...
ℹ️  Note: To install Dapr using Helm, see here: https://docs.dapr.io/getting-started/install-dapr-kubernetes/#install-with-helm-advanced

✅  Deploying the Dapr control plane to your cluster...
✅  Success! Dapr has been installed to namespace dapr-system. To verify, run `dapr status -k' in your terminal. To get started, go here: https://aka.ms/dapr-getting-started
```
2. Deploy app-a: `kubectl apply -f deployment-app-a.yaml`. The result should be similar to:
```
deployment.apps/app-a created
```
3. Deploy app-b: `kubectl apply -f deployment-app-b.yaml`. The result should be similar to:
```
deployment.apps/app-b created
```
4. Deploy app-a service: `kubectl apply -f svc-app-a.yaml`. The result should be similar to:
```
service/svc-app-a created
```
5. Deploy app-b service: `kubectl apply -f svc-app-b.yaml`. The result should be similar to:
```
service/svc-app-b created
```
6. Deploy ingress configuration a: `kubectl apply -f ingress-svc-app-a.yaml`. The result should be similar to:
```
ingress.networking.k8s.io/ingress-svc-app-a configured
```
7. Deploy ingress configuration b: `kubectl apply -f ingress-svc-app-b.yaml`. The result should be similar to:
```
ingress.networking.k8s.io/ingress-svc-app-b configured
```

The end result should be two running pods with two containers on your local Docker Desktop Kubernetes cluster.

You can verify that the set up is working by going to http://localhost:55555/app-a/ and http://localhost:55555/app-b/

Also, it should be possible to make a request to one app, which in turn makes a request to the other app:

http://localhost:55555/app-a/who2
http://localhost:55555/app-b/who2
