import { Portefeuille } from '../portefeuille/portefeuille';
import { User } from './user';

describe('User', () => {
  it('should create an instance', () => {
    expect(new User("", new Portefeuille(),"", "", "", "", "", "")).toBeTruthy();
  });
});
