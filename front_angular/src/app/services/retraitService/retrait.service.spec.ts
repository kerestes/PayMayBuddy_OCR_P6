import { TestBed } from '@angular/core/testing';

import { RetraitService } from './retrait.service';

xdescribe('RetraitService', () => {
  let service: RetraitService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RetraitService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
