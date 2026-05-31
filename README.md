# 🖥️ System Health Monitor

A production-style REST API built with **Java Spring Boot** that exposes 
real-time system metrics (CPU, memory, disk), containerized with **Docker** 
and deployed on **Kubernetes**.

## 🚀 Tech Stack
- Java 17 + Spring Boot 3.2
- Docker (multi-stage build)
- Kubernetes (Minikube)
- Maven

## 📡 API Endpoints
| Endpoint | Description |
|----------|-------------|
| GET /api/health | App status |
| GET /api/metrics/cpu | CPU usage & cores |
| GET /api/metrics/memory | RAM usage |
| GET /api/metrics/disk | Disk usage |
| GET /api/metrics/all | Full system snapshot |

## ⚙️ Run Locally
```bash
mvn spring-boot:run
```

## 🐳 Run with Docker
```bash
docker build -t health-monitor:1.0.0 .
docker run -p 8080:8080 health-monitor:1.0.0
```

## ☸️ Deploy to Kubernetes
```bash
minikube start
minikube image load health-monitor:1.0.0
kubectl apply -f kubernetes/
kubectl get pods
```
