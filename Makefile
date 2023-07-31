
build:
	docker compose build

run:
	docker compose up -d

build-and-run: build run

run-db:
	docker compose up -d reservations-db