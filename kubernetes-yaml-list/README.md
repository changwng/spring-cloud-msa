spring-cloud-msa
============

## 개요
#### 변환 툴을 사용하지 않고 한땀 한땀 작성한 yaml 파일입니다. 
cd kubernates-yaml-list 
awk 'FNR==1 && NR!=1  {print "---"}{print}' *.yaml | helmify msa-helm
templates 폴더에 들어가서 모든 service.yaml의 metadata.name: 에서 {{ include "msa-helm.fullname" . }}- 꼭 이걸 지워야 한다.

cd msa-helm
helm install spring-cloud-msa-app . --namespace msa-ns

helm install spring-cloud-msa-app . --namespace msa-ns --create-namespace
helm list -n msa-ns
kubectl get all -n msa-ns

kubectl create namespace msa-ns
kubectl config set-context --current --namespace=msa-ns