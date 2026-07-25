#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
LOG_DIR="${ROOT}/.run-logs"
mkdir -p "${LOG_DIR}"

start_service() {
  local module="$1"
  echo "Starting ${module}..."
  (
    cd "${ROOT}"
    nohup mvn -pl "${module}" -am spring-boot:run >"${LOG_DIR}/${module}.log" 2>&1 &
    echo $! >"${LOG_DIR}/${module}.pid"
  )
}

echo "Building all modules..."
cd "${ROOT}"
mvn -q -DskipTests package

start_service smartcity-registry
sleep 8
start_service smartcity-traffic
start_service smartcity-parking
start_service smartcity-food
start_service smartcity-shopping
start_service smartcity-energy
start_service smartcity-environment
sleep 5
start_service smartcity-city-scene
sleep 5
start_service smartcity-gateway

echo
echo "Smart city microservices launching."
echo "Gateway UI:  http://localhost:8080"
echo "Eureka:      http://localhost:8761"
echo "Logs:        ${LOG_DIR}"
echo "Stop with:   ${ROOT}/scripts/stop-all.sh"
