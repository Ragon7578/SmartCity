#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
LOG_DIR="${ROOT}/.run-logs"

if [[ -d "${LOG_DIR}" ]]; then
  for pidfile in "${LOG_DIR}"/*.pid; do
    [[ -f "${pidfile}" ]] || continue
    pid="$(cat "${pidfile}")"
    if kill -0 "${pid}" 2>/dev/null; then
      echo "Stopping PID ${pid} ($(basename "${pidfile}" .pid))"
      kill "${pid}" 2>/dev/null || true
    fi
    rm -f "${pidfile}"
  done
fi

pkill -f 'smartcity-(registry|gateway|city-scene|traffic|parking|food|shopping|energy|environment)' 2>/dev/null || true
echo "Stop signals sent."
