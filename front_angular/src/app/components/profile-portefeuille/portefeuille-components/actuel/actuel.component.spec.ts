import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActuelComponent } from './actuel.component';
import { PortefeuilleService } from '../../../../services/portefeuille/portefeuille.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ActuelComponent', () => {
  let component: ActuelComponent;
  let fixture: ComponentFixture<ActuelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [PortefeuilleService],
      imports: [ActuelComponent, HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActuelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
