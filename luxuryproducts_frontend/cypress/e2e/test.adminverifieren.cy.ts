describe('admin kan admim functionaliteit', ()=> {
    beforeEach(() => {
        cy.intercept('GET', 'http://localhost:8080/api/product', {
            fixture: 'products.json'
        }).as('getProducts');
        cy.getCategories()

        cy.intercept('POST', 'http://localhost:8080/api/auth/login', (req) => {
            const { email, password } = req.body;
            if (email === 'admin@gmail.com' && password === 'Welom01!') {
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

        cy.intercept('GET', 'http://localhost:8080/api/giftcard/all', {
            fixture: 'giftcardadmin.json'
        }).as('getGiftcard');

        cy.intercept('GET', 'http://localhost:8080/api/giftcard', {
            fixture: 'giftcardadminme.json'
        }).as('getGiftcardMe');

        cy.intercept('PUT', 'http://localhost:8080/api/block/1', {
            statusCode: 200,
            body: {
                message: 'geblokkeerd'
            }
        })
        cy.intercept('PUT', 'http://localhost:8080/api/price/1', {
            statusCode: 200,
            body: {
                message: 'price'
            }
        })
    })

    it('moet kunnen zoeken naar giftcard, naar email en giftcard kunnen blokkeren', () => {
        cy.visit('http://localhost:26338');

        cy.get('[data-testid="login-button"]').should('be.visible').click();

        cy.get('input[type="email"]').type("admin@gmail.com");
        cy.get('input[type="password"]').type("welkom01!");
        cy.get('button[type="submit"]').click();

        cy.visit('http://localhost:26338/searchgiftcard');

        cy.get('input[placeholder="Zoek op e-mail"]').type('admin@gmail.com');
        cy.contains('admin@gmail.com').should('be.visible');
        cy.get('input[placeholder="Zoek op e-mail"]').clear()

        cy.get('input[placeholder="Zoek op e-mail"]').type('testuser@gmail.com');
        cy.contains('admin@gmail.com').should('not.exist');
        cy.get('input[placeholder="Zoek op e-mail"]').clear()

        cy.get('input[placeholder="Zoek op giftcardcode"]').type('LP12345678901234');
        cy.contains('LP12345678901234').should('be.visible');
        cy.contains('LP23456789012345').should('not.exist');

        cy.contains('Blokkeer giftcard').should('be.visible').click();

        cy.visit('http://localhost:26338/checkout');

        cy.contains('Kies een giftcard').click();

        cy.contains('Er zijn 1 geblokkeerde giftcards die niet gebruikt kunnen worden: ').should('be.visible');

        cy.contains('Code: LP12345678901234 - Waarde: €100').should('be.visible');


    })


});