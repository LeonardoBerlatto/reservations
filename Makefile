
image:
	docker compose build

run:
	docker compose up -d

build-and-run: image run

run-db:
	docker compose up -d reservations-db