import { TestBed } from '@angular/core/testing';

import { TrasferService } from './trasfer.service';

describe('TrasferService', () => {
  let service: TrasferService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrasferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
