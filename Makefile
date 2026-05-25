.PHONY: run build stop clean logs test

run:
	docker-compose up

build:
	docker-compose up --build

stop:
	docker-compose down

clean:
	docker-compose down -v

logs:
	docker-compose logs -f

test:
	cd backend && ./mvnw test