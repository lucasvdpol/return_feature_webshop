describe("Check if request can be filtered", () => {
    beforeEach(() => {
        cy.getProducts()
        cy.visit('/');
        cy.get('[data-testid="login-button"]').click();
        cy.get('input[type="email"]').type('admin@gmail.com');
        cy.get('input[type="password"]').type('Welkom01!');
        cy.get('button[type="submit"]').click();
        cy.loginAsAdmin()
        cy.get('.login-popup').should('not.exist');
        cy.get('[data-testid="account-button"]').should('be.visible');
        cy.visit('/account');
        cy.getAllReturnRequests()
        cy.get('[data-testid="allreturns-button"]').click({force: true});
    });
    it('Should be able to filter request with right id', () => {
        cy.get('.request').should('exist').and('be.visible');
        cy.get('.search-bar').type('1')
        cy.get('.request').should('exist');

    });

    it('Should be able to filter request with wrong id', () => {
        cy.get('.request').should('exist').and('be.visible');
        cy.get('.search-bar').type('2')
        cy.get('.request').should('not.exist');

    });
})

