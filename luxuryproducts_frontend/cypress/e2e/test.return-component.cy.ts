describe('Return request flow', () => {
    beforeEach(() => {
        cy.getProducts()
        cy.visit('/');
        cy.get('[data-testid="login-button"]').click();
        cy.get('input[type="email"]').type('admin@gmail.com');
        cy.get('input[type="password"]').type('Welkom01!');
        cy.get('button[type="submit"]').click();
        cy.login()
        cy.get('.login-popup').should('not.exist');

        cy.get('[data-testid="account-button"]').should('be.visible');
        cy.visit('/account');
        cy.get('[data-testid="vieworder-button"]').click();
        cy.getOrders()
        cy.get('button.retour').first().click({force: true});
        cy.url().should('include', '/return/order');
        cy.get('.product-row').should('exist');
    });
    it('should be able to request a return', () => {
        cy.get('.product-row').first().find('input[type=checkbox]').check();
        cy.get('select.reason-select').first().select('beschadigd');
        cy.get('button[type="submit"]').click();
        cy.url().should('include', '/return/confirmation')

    });
    it('should get alert with no items', () => {

        cy.get('button[type="submit"]').click();
        cy.on('window:alert', (text) => {
            expect(text).to.contain('Selecteer minstens één product om te retourneren');
        });
        cy.url().should('not.include', '/confirmation');
    })
    it('should get alert with no reason', () => {
        cy.get('button[type="submit"]').click();
        cy.on('window:alert', (text) => {
            expect(text).to.contain('Selecteer een reden');
        });
        cy.url().should('not.include', '/confirmation');
    });
    it('should contain returncosts with not necessary reason', () => {
        cy.get('.product-row').first().find('input[type=checkbox]').check();
        cy.get('select.reason-select').first().select('niet nodig');
        cy.get('.return-cost-warning').should('be.visible');
    });
    it('should contain returncosts with not other reason', () => {
        cy.get('.product-row').first().find('input[type=checkbox]').check();
        cy.get('select.reason-select').first().select('anders');
        cy.get('.return-cost-warning').should('be.visible');
    });
});


