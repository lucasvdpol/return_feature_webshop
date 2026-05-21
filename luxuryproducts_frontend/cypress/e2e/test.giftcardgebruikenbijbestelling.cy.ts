describe( "gfitcard gebruiken bij een bestelling", () =>{

    beforeEach( () => {
        let giftcarddata = [
            {
                id: 1,
                giftcardCode: "LP12345678901234",
                date: "2025-06-01T14:30:00Z",
                endDate: "2027-06-01T14:30:00Z",
                price: 100,
                geschiedenis: "",
                blocked: false,
                user: {
                    id: 1,
                    email: "testuser@example.com",
                    password: "Welkom01!"
                }
            }
        ];

        const orderMock = {
            id: 1,
            orderDate: '2025-06-01',
            customerName: "testuser@example.com",
            winkelwagen: [
                {
                    productName: 'Giftcard van €100',
                    quantity: 1,
                    price: 100,
                    giftcardCode: "LP12345678901234",
                }
            ],
            totalPrice: 100
        };

        cy.intercept('GET', 'http://localhost:8080/api/product', {
        fixture: 'products.json'
    }).as('getProducts');

    cy.intercept('POST', 'http://localhost:8080/api/auth/login', (req) => {
        const { email, password } = req.body;
        if (email === 'testuser@example.com' && password === 'Welom01!') {
            req.reply({
                statusCode: 401,
                body: {
                    message: 'Invalid credentials'
                }
            });
        } else {
            req.reply({
                statusCode: 200,
                body: {
                    token: 'fake-token',
                    user: {
                        email,
                        role: 'admin'
                    }
                }
            });
        }
    }).as('loginRequest');

    cy.intercept('GET', 'http://localhost:8080/api/giftcard', (req) => {
        req.reply(giftcarddata)
    }).as('getGiftcard');

    cy.intercept('PUT', 'http://localhost:8080/api/giftcard/emailUser/LP12345678901234', {
        statusCode: 200,
        body: {
            message: 'Email address aangepast',
        }
    })

    cy.intercept('PUT', 'http://localhost:8080/api/giftcard/price/1', (req) => {
        giftcarddata[0].price -= 100;
        giftcarddata[0].geschiedenis = "Betaling gedaan";
        req.reply({
            statusCode: 200,
            body: {
                message: 'Price aangepast',
            }
        })
    })
        cy.intercept('POST', '/api/orderEntity', {
            statusCode: 200,
            body: {giftcardCode: "LP12345678901234"}
        }).as('placeOrder');

        cy.intercept('GET', '/api/orderEntity', {
            statusCode: 200,
            body: [orderMock]
        }).as('accountGiftcards');
    });

    it('moet giftcard kunnen gebruiken bij bestelling', () => {
        cy.visit('http://localhost:26338');

        cy.get('[data-testid="login-button"]').should('be.visible').click();

        cy.get('input[type="email"]').type("testuser@example.com");
        cy.get('input[type="password"]').type("welkom01!");
        cy.get('button[type="submit"]').click();

        cy.visit('http://localhost:26338/checkout');

        cy.window().then((win) => {
            const w = win as any;
            w.winkelmandService.addToCart('test Product', 1, 7999, "1");
        });

        cy.contains('Koppel giftcard met code').click();

        cy.get('input[type="text"]').type('LP12345678901234');
        cy.get('input[type="email"]').type('testuser@example.com');
        cy.get('button[type="submit"]').click();

        cy.contains('Kies een giftcard').click();

        cy.contains('Code: LP12345678901234 - Waarde: €100').click();

        cy.contains('7899').should('be.visible');
        cy.get('form input').each(($input) => {
            cy.wrap($input).type('a');
        });

        cy.contains('Plaats bestelling').click();

        cy.visit('/accountgiftcards');
        cy.contains('Toon verlopen of geblokkeerde giftcards').click();

        cy.contains('Waarde: €0').should('be.visible');

    })


})