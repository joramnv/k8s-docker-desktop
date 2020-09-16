Steps to setup cluster:

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
2. Wait a few seconds for the previous deploy to take effect. If you execute the scripts too fast after each other you might see an error similar to:
```
Error from server (InternalError): error when creating "minimal-ingress.yaml": Internal error occurred: failed calling webhook "validate.nginx.ingress.kubernetes.io": Post https://ingress-nginx-controller-admission.ingress-nginx.svc:443/extensions/v1beta1/ingresses?timeout=30s: dial tcp 10.108.239.198:443: connect: connection refused
```
3. Deploy ingress configuration: `kubectl apply -f ingress-setup.yaml`. The result should be similar to:
```
ingress.networking.k8s.io/ingress-setup created
```
4. Deploy ktor-demo service: `kubectl apply -f ktor-demo-svc.yaml`. The result should be similar to:
```
service/ktor-demo-svc created
```
5. Deploy ktor-demo pod: `kubectl apply -f ktor-demo-pod.yaml`.  The result should be similar to:
```
pod/ktor-demo-server created
```

Now the container in the pod can be accessed via http://localhost:55555/testpath/ and paths like http://localhost:55555/testpath/json/gson work too. 

- With this setup, port 80 of the host machine is not being used for the cluster. Instead port 55555 is used for normal HTTP trafic.
- Port 443 is used for HTTPS trafic.
- Steps 3 and 4 could potentially be merged.