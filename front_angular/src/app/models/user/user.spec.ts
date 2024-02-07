import { Portefeuille } from '../portefeuille/portefeuille';
import { User } from './user';

xdescribe('User', () => {
  it('should create an instance', () => {
    expect(new User()).toBeTruthy();
  });
});
