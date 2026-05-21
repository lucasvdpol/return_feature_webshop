describe("Check return status", () => {
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
    });
    it('Should be able to check return status', () => {
        cy.visit('/account');
        cy.getReturnRequests()
        cy.get('[data-testid="return-button"]').click();
        cy.get('.return-header').should('contain', '#')
        cy.get('.status').should('contain', 'Retour aangevraagd');


    });
})

