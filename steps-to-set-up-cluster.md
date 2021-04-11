Steps to set up cluster:

1. Deploy basic ingress setup: `kubectl apply -f ingress-nginx-foundation.yaml`. The result should be similar to:
```
namespace/ingress-nginx created
serviceaccount/ingress-nginx created
configmap/ingress-nginx-controller created
clusterrole.rbac.authorization.k8s.io/ingress-nginx created
clusterrolebinding.rbac.authorization.k8s.io/ingress-nginx created
role.rbac.authorization.k8s.io/ingress-nginx created
rolebinding.rbac.authorization.k8s.io/ingress-nginx created
service/ingress-nginx-controller-admission created
service/ingress-nginx-controller created
deployment.apps/ingress-nginx-controller created
validatingwebhookconfiguration.admissionregistration.k8s.io/ingress-nginx-admission created
serviceaccount/ingress-nginx-admission created
clusterrole.rbac.authorization.k8s.io/ingress-nginx-admission created
clusterrolebinding.rbac.authorization.k8s.io/ingress-nginx-admission created
role.rbac.authorization.k8s.io/ingress-nginx-admission created
rolebinding.rbac.authorization.k8s.io/ingress-nginx-admission created
job.batch/ingress-nginx-admission-create created
job.batch/ingress-nginx-admission-patch created
```
- Note - Wait a few seconds for the previous deploy to take effect. If you execute the scripts too fast after each other you might see an error similar to:
```
Error from server (InternalError): error when creating "ingress-svc-app-a.yaml": Internal error occurred: failed calling webhook "validate.nginx.ingress.kubernetes.io": Post https://ingress-nginx-controller-admission.ingress-nginx.svc:443/extensions/v1beta1/ingresses?timeout=30s: dial tcp 10.108.239.198:443: connect: connection refused
```
2. Deploy app-a: `kubectl apply -f deployment-app-a-initial.yaml`. The result should be similar to:
```
deployment.apps/app-a created
```
3. Deploy app-a service: `kubectl apply -f svc-app-a.yaml`. The result should be similar to:
```
service/svc-app-a created
```
4. Deploy ingress configuration a: `kubectl apply -f ingress-svc-app-a.yaml`. The result should be similar to:
```
ingress.networking.k8s.io/ingress-svc-app-a configured
```

The end result should be a running pod with one container on your local Docker Desktop Kubernetes cluster.

You can verify that the set up is working by going to http://localhost:55555/app-a/

Some things worth mentioning:
- With this setup, port 80 of the host machine is not being used for the cluster. Instead, port 55555 is used for normal HTTP traffic.
- Port 443 is used for HTTPS traffic.
